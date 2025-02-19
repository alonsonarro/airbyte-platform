/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.commons.server.scheduler;

import io.airbyte.commons.temporal.JobMetadata;
import io.airbyte.config.JobConfig.ConfigType;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Job metadata for synchronous jobs. Provides common interface for this metadata to make handling
 * all synchronous requests easier.
 */
public class SynchronousJobMetadata {

  private final UUID id;
  private final ConfigType configType;
  private final UUID configId;

  private final long createdAt;
  private final long endedAt;
  private final boolean succeeded;
  private final boolean connectorConfigurationUpdated;

  private final Path logPath;

  /**
   * Create synchronous job metadata from a temporal response.
   *
   * @param jobMetadata temporal job metadata
   * @param id job id
   * @param configType job type
   * @param configId id of resource for job type (i.e. if configType is discover config id is going to
   *        be a source id)
   * @param connectorConfigurationUpdated whether this job updated the connector configuration
   * @param createdAt time the job was created
   * @param endedAt time the job ended
   * @return synchronous job metadata
   */
  public static SynchronousJobMetadata fromJobMetadata(final JobMetadata jobMetadata,
                                                       final UUID id,
                                                       final ConfigType configType,
                                                       final UUID configId,
                                                       final boolean connectorConfigurationUpdated,
                                                       final long createdAt,
                                                       final long endedAt) {
    return new SynchronousJobMetadata(
        id,
        configType,
        configId,
        createdAt,
        endedAt,
        jobMetadata.isSucceeded(),
        connectorConfigurationUpdated,
        jobMetadata.getLogPath());
  }

  public SynchronousJobMetadata(final UUID id,
                                final ConfigType configType,
                                final UUID configId,
                                final long createdAt,
                                final long endedAt,
                                final boolean succeeded,
                                final boolean connectorConfigurationUpdated,
                                final Path logPath) {
    this.id = id;
    this.configType = configType;
    this.configId = configId;
    this.createdAt = createdAt;
    this.endedAt = endedAt;
    this.succeeded = succeeded;
    this.connectorConfigurationUpdated = connectorConfigurationUpdated;
    this.logPath = logPath;
  }

  public UUID getId() {
    return id;
  }

  public ConfigType getConfigType() {
    return configType;
  }

  public Optional<UUID> getConfigId() {
    return Optional.ofNullable(configId);
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public long getEndedAt() {
    return endedAt;
  }

  public boolean isSucceeded() {
    return succeeded;
  }

  public boolean isConnectorConfigurationUpdated() {
    return connectorConfigurationUpdated;
  }

  public Path getLogPath() {
    return logPath;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final SynchronousJobMetadata that = (SynchronousJobMetadata) o;
    return createdAt == that.createdAt && endedAt == that.endedAt && succeeded == that.succeeded
        && connectorConfigurationUpdated == that.connectorConfigurationUpdated && Objects.equals(id, that.id)
        && configType == that.configType && Objects.equals(configId, that.configId) && Objects.equals(logPath, that.logPath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, configType, configId, createdAt, endedAt, succeeded, connectorConfigurationUpdated, logPath);
  }

  @Override
  public String toString() {
    return "SynchronousJobMetadata{"
        + "id=" + id
        + ", configType=" + configType
        + ", configId=" + configId
        + ", createdAt=" + createdAt
        + ", endedAt=" + endedAt
        + ", succeeded=" + succeeded
        + ", connectorConfigurationUpdated=" + connectorConfigurationUpdated
        + ", logPath=" + logPath
        + '}';
  }

  /**
   * Create an empty object. This is used because some API interfaces assume that there will be this
   * metadata for jobs that don't produce it. This method is a convenience method to shim an empty
   * version of the metadata in those cases.
   *
   * @param configType config type
   * @return empty synchronous job metadata
   */
  public static SynchronousJobMetadata mock(final ConfigType configType) {
    final long now = Instant.now().toEpochMilli();
    final UUID configId = null;
    final boolean succeeded = true;
    final boolean connectorConfigurationUpdated = false;
    final Path logPath = null;

    return new SynchronousJobMetadata(
        UUID.randomUUID(),
        configType,
        configId,
        now,
        now,
        succeeded,
        connectorConfigurationUpdated,
        logPath);
  }

}
