/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.server.apis;

import static io.airbyte.commons.auth.AuthRoleConstants.AUTHENTICATED_USER;

import io.airbyte.api.generated.DestinationDefinitionSpecificationApi;
import io.airbyte.api.model.generated.DestinationDefinitionIdWithWorkspaceId;
import io.airbyte.api.model.generated.DestinationDefinitionSpecificationRead;
import io.airbyte.commons.server.handlers.SchedulerHandler;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@SuppressWarnings("MissingJavadocType")
@Controller("/api/v1/destination_definition_specifications")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class DestinationDefinitionSpecificationApiController implements DestinationDefinitionSpecificationApi {

  private final SchedulerHandler schedulerHandler;

  public DestinationDefinitionSpecificationApiController(final SchedulerHandler schedulerHandler) {
    this.schedulerHandler = schedulerHandler;
  }

  @SuppressWarnings("LineLength")
  @Post("/get")
  @Secured({AUTHENTICATED_USER})
  @ExecuteOn(TaskExecutors.IO)
  @Override
  public DestinationDefinitionSpecificationRead getDestinationDefinitionSpecification(final DestinationDefinitionIdWithWorkspaceId destinationDefinitionIdWithWorkspaceId) {
    return ApiHelper.execute(() -> schedulerHandler.getDestinationSpecification(destinationDefinitionIdWithWorkspaceId));
  }

}
