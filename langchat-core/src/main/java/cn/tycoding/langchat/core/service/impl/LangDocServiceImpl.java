/*
 * Copyright (c) 2024 LangChat. TyCoding All Rights Reserved.
 *
 * Licensed under the GNU Affero General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.gnu.org/licenses/agpl-3.0.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.tycoding.langchat.core.service.impl;

import cn.tycoding.langchat.common.dto.ChatReq;
import cn.tycoding.langchat.common.dto.EmbeddingR;
import cn.tycoding.langchat.core.provider.EmbedProvider;
import cn.tycoding.langchat.core.provider.ModelProvider;
import cn.tycoding.langchat.core.service.Assistant;
import cn.tycoding.langchat.core.service.LangDocService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.store.embedding.filter.Filter;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static cn.tycoding.langchat.core.consts.EmbedConst.FILENAME;
import static cn.tycoding.langchat.core.consts.EmbedConst.KNOWLEDGE;
import static dev.langchain4j.data.document.Metadata.metadata;
import static dev.langchain4j.store.embedding.filter.MetadataFilterBuilder.metadataKey;

/**
 * @author tycoding
 * @since 2024/4/4
 */
@Slf4j
@Service
@AllArgsConstructor
public class LangDocServiceImpl implements LangDocService {

    private final EmbedProvider embedProvider;
    private final ModelProvider modelProvider;
    private final PgVectorEmbeddingStore embeddingStore;

    @Override
    public EmbeddingR embeddingText(ChatReq req) {
        TextSegment segment = TextSegment.from(req.getMessage(),
                metadata(KNOWLEDGE, req.getKnowledgeId()).put(FILENAME, req.getDocsName()));
        EmbeddingModel embeddingModel = embedProvider.embed();
        Embedding embedding = embeddingModel.embed(segment).content();

        String id = embeddingStore.add(embedding, segment);
        return new EmbeddingR().setVectorId(id).setText(segment.text());
    }

    @Override
    public List<EmbeddingR> embeddingDocs(ChatReq req) {
        EmbeddingModel model = embedProvider.embed();

        Document document;
        if (req.getUrl().startsWith("http")) {
            document = UrlDocumentLoader.load(req.getUrl(), new ApacheTikaDocumentParser());
        } else {
            document = FileSystemDocumentLoader.loadDocument(req.getUrl(), new ApacheTikaDocumentParser());
        }
        document.metadata().put(KNOWLEDGE, req.getKnowledgeId()).put(FILENAME, req.getDocsName());

        DocumentSplitter splitter = EmbedProvider.splitter(req.getModelName(), req.getModelProvider());
        List<TextSegment> segments = splitter.split(document);
        List<Embedding> embeddings = model.embedAll(segments).content();
        List<String> ids = embeddingStore.addAll(embeddings, segments);

        List<EmbeddingR> list = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            list.add(new EmbeddingR().setVectorId(ids.get(i)).setText(segments.get(i).text()));
        }
        return list;
    }

    @Override
    public TokenStream chat(ChatReq req) {
        StreamingChatLanguageModel chatLanguageModel = modelProvider.stream(req.getModelId());
        AiServices<Assistant> aiServices = AiServices.builder(Assistant.class)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(5))
                .streamingChatLanguageModel(chatLanguageModel);

        EmbeddingModel model = embedProvider.embed();
        Function<Query, Filter> filterByUserId = (query) -> metadataKey(KNOWLEDGE).isEqualTo(req.getKnowledgeId());
        ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(model)
                .dynamicFilter(filterByUserId)
                .build();
        aiServices.contentRetriever(contentRetriever);

        Assistant assistant = aiServices.build();
        return assistant.stream(req.getConversationId(), req.getMessage());
    }
}
