// Copyright 2018 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.devtools.build.lib.skylarkbuildapi.apple;

import com.google.devtools.build.lib.skylarkbuildapi.FileApi;
import com.google.devtools.build.lib.skylarkbuildapi.core.ProviderApi;
import com.google.devtools.build.lib.skylarkbuildapi.core.StructApi;
import com.google.devtools.build.lib.skylarkinterface.Param;
import com.google.devtools.build.lib.skylarkinterface.SkylarkCallable;
import com.google.devtools.build.lib.skylarkinterface.StarlarkBuiltin;
import com.google.devtools.build.lib.skylarkinterface.StarlarkConstructor;
import com.google.devtools.build.lib.skylarkinterface.StarlarkDocumentationCategory;
import com.google.devtools.build.lib.syntax.EvalException;

/**
 * Interface for an info type containing information regarding multi-architecture Apple static
 * libraries.
 */
@StarlarkBuiltin(
    name = "AppleStaticLibrary",
    category = StarlarkDocumentationCategory.PROVIDER,
    doc =
        "A provider containing information regarding multi-architecture Apple static libraries, "
            + "as is propagated by the apple_static_library rule.")
public interface AppleStaticLibraryInfoApi extends StructApi {

  /** Starlark name for this interface. */
  String STARLARK_NAME = "AppleStaticLibrary";

  @SkylarkCallable(
      name = "archive",
      structField = true,
      doc = "The multi-arch archive (.a) output by apple_static_library.")
  FileApi getMultiArchArchive();

  @SkylarkCallable(
      name = "objc",
      structField = true,
      doc =
          "A provider which contains information about the transitive dependencies linked into "
              + "the archive.")
  ObjcProviderApi<?> getDepsObjcProvider();

  /** Interface for the provider type for {@link AppleStaticLibraryInfoApi}. */
  interface AppleStaticLibraryInfoProvider<
          FileApiT extends FileApi, ObjcProviderApiT extends ObjcProviderApi<?>>
      extends ProviderApi {

    @SkylarkCallable(
        name = STARLARK_NAME,
        doc = "The <code>AppleStaticLibrary</code> constructor.",
        parameters = {
          @Param(
              name = "archive",
              type = FileApi.class,
              named = true,
              positional = false,
              doc = "Multi-architecture archive (.a) representing a static library"),
          @Param(
              name = "objc",
              type = ObjcProviderApi.class,
              named = true,
              positional = false,
              doc =
                  "A provider which contains information about the transitive dependencies "
                      + "linked into the archive."),
        },
        selfCall = true)
    @StarlarkConstructor(objectType = AppleStaticLibraryInfoApi.class)
    AppleStaticLibraryInfoApi appleStaticLibrary(FileApiT archive, ObjcProviderApiT objcProvider)
        throws EvalException;
  }
}
