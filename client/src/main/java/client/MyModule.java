/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client;

import client.scenes.*;
import client.service.MessageLogicService;
import client.service.MessageLogicServiceImpl;
import client.usecase.RememberUsernameUseCaseImpl;
import client.usecase.RememberUsernamesUseCase;
import client.usecase.ResolveImageUrlsUseCase;
import client.usecase.ResolveImageUrlsUseCaseImpl;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

import client.service.ServerServiceImpl;
import client.service.ServerService;

public class MyModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
		binder.bind(OpeningCtrl.class).in(Scopes.SINGLETON);
		binder.bind(AdminCtrl.class).in(Scopes.SINGLETON);
		binder.bind(LeaderboardCtrl.class).in(Scopes.SINGLETON);
		binder.bind(IntermediateLeaderboardCtrl.class).in(Scopes.SINGLETON);
		binder.bind(ServerService.class).to(ServerServiceImpl.class).in(Scopes.SINGLETON);
		binder.bind(MessageLogicService.class).to(MessageLogicServiceImpl.class).in(Scopes.SINGLETON);
		binder.bind(EndingScreenCtrl.class).in(Scopes.SINGLETON);
		binder.bind(ResolveImageUrlsUseCase.class).to(ResolveImageUrlsUseCaseImpl.class).in(Scopes.SINGLETON);
		binder.bind(RememberUsernamesUseCase.class).to(RememberUsernameUseCaseImpl.class).in(Scopes.SINGLETON);
	}
}
