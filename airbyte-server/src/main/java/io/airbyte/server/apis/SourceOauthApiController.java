/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.server.apis;

import static io.airbyte.commons.auth.AuthRoleConstants.ADMIN;
import static io.airbyte.commons.auth.AuthRoleConstants.EDITOR;

import io.airbyte.api.generated.SourceOauthApi;
import io.airbyte.api.model.generated.CompleteSourceOauthRequest;
import io.airbyte.api.model.generated.OAuthConsentRead;
import io.airbyte.api.model.generated.SetInstancewideSourceOauthParamsRequestBody;
import io.airbyte.api.model.generated.SourceOauthConsentRequest;
import io.airbyte.commons.auth.SecuredWorkspace;
import io.airbyte.commons.server.handlers.OAuthHandler;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import java.util.Map;

@SuppressWarnings("MissingJavadocType")
@Controller("/api/v1/source_oauths")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class SourceOauthApiController implements SourceOauthApi {

  private final OAuthHandler oAuthHandler;

  @SuppressWarnings("ParameterName")
  public SourceOauthApiController(final OAuthHandler oAuthHandler) {
    this.oAuthHandler = oAuthHandler;
  }

  @Post("/complete_oauth")
  @Secured({EDITOR})
  @SecuredWorkspace
  @ExecuteOn(TaskExecutors.IO)
  @Override
  public Map<String, Object> completeSourceOAuth(@Body final CompleteSourceOauthRequest completeSourceOauthRequest) {
    return ApiHelper.execute(() -> oAuthHandler.completeSourceOAuthHandleReturnSecret(completeSourceOauthRequest));
  }

  @Post("/get_consent_url")
  @Secured({EDITOR})
  @SecuredWorkspace
  @ExecuteOn(TaskExecutors.IO)
  @Override
  public OAuthConsentRead getSourceOAuthConsent(@Body final SourceOauthConsentRequest sourceOauthConsentRequest) {
    return ApiHelper.execute(() -> oAuthHandler.getSourceOAuthConsent(sourceOauthConsentRequest));
  }

  @Post("/oauth_params/create")
  @Secured({ADMIN})
  @ExecuteOn(TaskExecutors.IO)
  @Override
  public void setInstancewideSourceOauthParams(
                                               @Body final SetInstancewideSourceOauthParamsRequestBody setInstancewideSourceOauthParamsRequestBody) {
    ApiHelper.execute(() -> {
      oAuthHandler.setSourceInstancewideOauthParams(setInstancewideSourceOauthParamsRequestBody);
      return null;
    });
  }

}
