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

package cn.tycoding.langchat.ai.biz.service;

import cn.tycoding.langchat.ai.biz.entity.AigcConversation;
import cn.tycoding.langchat.ai.biz.entity.AigcMessage;
import cn.tycoding.langchat.common.core.utils.QueryPage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author tycoding
 * @since 2024/1/4
 */
public interface AigcMessageService extends IService<AigcMessage> {

    /**
     * 获取会话列表
     */
    List<AigcConversation> conversations(String userId);

    /**
     * 获取会话分页列表
     */
    IPage<AigcConversation> conversationPages(AigcConversation data, QueryPage queryPage);

    /**
     * 新增会话
     */
    AigcConversation addConversation(AigcConversation conversation);

    /**
     * 修改会话
     */
    void updateConversation(AigcConversation conversation);

    /**
     * 删除会话
     */
    void delConversation(String conversationId);

    AigcMessage addMessage(AigcMessage message);

    void clearMessage(String conversationId);

    List<AigcMessage> getMessages(String conversationId);

    List<AigcMessage> getMessages(String conversationId, String userId);
}

