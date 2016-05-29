/**
 * Copyright (c) 2015-2016, Michael Yang 杨福海 (fuhai999@gmail.com).
 *
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jpress.router.converter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jpress.Consts;
import io.jpress.core.Jpress;
import io.jpress.model.Taxonomy;
import io.jpress.router.RouterConverter;

public class TaxonomyRouter extends RouterConverter {

	@Override
	public String converter(String target, HttpServletRequest request, HttpServletResponse response) {

		String[] targetDirs = parseTarget(target);

		if (targetDirs == null || targetDirs.length != 1) {
			return null;
		}

		String[] params = parseParam(targetDirs[0]);
		if (params == null || params.length == 0) {
			return null;
		}

		String moduleName = params[0];
		if (Jpress.currentTemplate().getModuleByName(moduleName) != null) {
			return Consts.ROUTER_TAXONOMY + target;
		}

		return null;
	}

	private static String getRouterWithoutSuffix(Taxonomy taxonomy) {
		String url = SLASH + taxonomy.getContentModule() + URL_PARA_SEPARATOR
				+ (taxonomy.getSlug() == null ? taxonomy.getId() : taxonomy.getSlug()) + URL_PARA_SEPARATOR + 1;

		return url;
	}

	public static String getRouter(Taxonomy taxonomy) {
		String url = getRouterWithoutSuffix(taxonomy);
		if (enalbleFakeStatic()) {
			url += getFakeStaticSuffix();
		}
		return url;
	}

	public static String getRouter(Taxonomy taxonomy, int pageNumber) {
		String url = getRouterWithoutSuffix(taxonomy);
		if (enalbleFakeStatic()) {
			return url + URL_PARA_SEPARATOR + pageNumber + getFakeStaticSuffix();
		}
		return url + URL_PARA_SEPARATOR + pageNumber;
	}

}
