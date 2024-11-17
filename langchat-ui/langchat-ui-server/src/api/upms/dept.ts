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

import { http } from '@/utils/http/axios';

export function list(params: any) {
  return http.request({
    url: '/upms/dept/list',
    method: 'get',
    params,
  });
}

export function tree(params: any) {
  return http.request({
    url: '/upms/dept/tree',
    method: 'get',
    params,
  });
}

export function getById(id: string) {
  return http.request({
    url: `/upms/dept/${id}`,
    method: 'get',
  });
}

export function add(params: any) {
  return http.request({
    url: '/upms/dept',
    method: 'post',
    params,
  });
}

export function update(params: any) {
  return http.request({
    url: '/upms/dept',
    method: 'put',
    params,
  });
}

export function del(id?: string) {
  return http.request({
    url: `/upms/dept/${id}`,
    method: 'delete',
  });
}
