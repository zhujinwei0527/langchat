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

export function getReqChartBy30() {
  return http.request({
    url: `/aigc/statistic/requestBy30`,
    method: 'get',
  });
}

export function getReqChart() {
  return http.request({
    url: `/aigc/statistic/request`,
    method: 'get',
  });
}

export function getTokenChartBy30() {
  return http.request({
    url: `/aigc/statistic/tokenBy30`,
    method: 'get',
  });
}

export function getTokenChart() {
  return http.request({
    url: `/aigc/statistic/token`,
    method: 'get',
  });
}

export function getHomeData() {
  return http.request({
    url: `/aigc/statistic/home`,
    method: 'get',
  });
}
