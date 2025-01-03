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

export interface ProjectSettingState {
  //导航模式
  navMode: string;
  //导航风格
  navTheme: string;
  //顶部设置
  headerSetting: object;
  //页脚
  showFooter: boolean;
  //菜单设置
  menuSetting: object;
  //多标签
  multiTabsSetting: object;
  //面包屑
  crumbsSetting: object;
  //权限模式
  permissionMode: string;
}

export interface IBodySetting {
  fixed: boolean;
}

export interface IHeaderSetting {
  bgColor: string;
  fixed: boolean;
  isReload: boolean;
}

export interface IMenuSetting {
  minMenuWidth: number;
  menuWidth: number;
  fixed: boolean;
  mixMenu: boolean;
  collapsed: boolean;
  mobileWidth: number;
}

export interface ICrumbsSetting {
  show: boolean;
  showIcon: boolean;
}

export interface IMultiTabsSetting {
  bgColor: string;
  fixed: boolean;
  show: boolean;
}
export interface GlobConfig {
  title: string;
  apiUrl: string;
  shortName: string;
  urlPrefix?: string;
  uploadUrl?: string;
  imgUrl?: string;
}

export interface GlobEnvConfig {
  // 标题
  VITE_GLOB_APP_TITLE: string;
  // 接口地址
  VITE_GLOB_API_URL: string;
  // 接口前缀
  VITE_GLOB_API_URL_PREFIX?: string;
  // Project abbreviation
  VITE_GLOB_APP_SHORT_NAME: string;
  // 图片上传地址
  VITE_GLOB_UPLOAD_URL?: string;
  //图片前缀地址
  VITE_GLOB_IMG_URL?: string;
}
