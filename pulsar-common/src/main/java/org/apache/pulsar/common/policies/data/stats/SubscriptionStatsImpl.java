/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.pulsar.common.policies.data.stats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Data;
import org.apache.pulsar.common.policies.data.SubscriptionStats;

/**
 * Statistics about subscription.
 */
@Data
public class SubscriptionStatsImpl implements SubscriptionStats {
    /** Total rate of messages delivered on this subscription (msg/s). */
    public double msgRateOut;

    /** Total throughput delivered on this subscription (bytes/s). */
    public double msgThroughputOut;

    /** Total bytes delivered to consumer (bytes). */
    public long bytesOutCounter;

    /** Total messages delivered to consumer (msg). */
    public long msgOutCounter;

    /** Total rate of messages redelivered on this subscription (msg/s). */
    public double msgRateRedeliver;

    /**
     * Total rate of message ack(msg/s).
     */
    public double messageAckRate;

    /** Chunked message dispatch rate. */
    public double chunkedMessageRate;

    /** Number of entries in the subscription backlog. */
    public long msgBacklog;

    /** Size of backlog in byte, -1 means that the argument "subscriptionBacklogSize" is false when calling the API. **/
    public long backlogSize;

    /** Get the publish time of the earliest message in the backlog. */
    public long earliestMsgPublishTimeInBacklog;

    /** Number of entries in the subscription backlog that do not contain the delay messages. */
    public long msgBacklogNoDelayed;

    /** Flag to verify if subscription is blocked due to reaching threshold of unacked messages. */
    public boolean blockedSubscriptionOnUnackedMsgs;

    /** Number of delayed messages currently being tracked. */
    public long msgDelayed;

    /** Number of messages registered for replay. */
    public long msgInReplay;

    /**
     * Number of unacknowledged messages for the subscription, where an unacknowledged message is one that has been
     * sent to a consumer but not yet acknowledged. Calculated by summing all {@link ConsumerStatsImpl#unackedMessages}
     * for this subscription. See {@link ConsumerStatsImpl#unackedMessages} for additional details.
     */
    public long unackedMessages;

    /** The subscription type as defined by {@link org.apache.pulsar.client.api.SubscriptionType}.  */
    public String type;

    /** The name of the consumer that is active for single active consumer subscriptions i.e. failover or exclusive. */
    public String activeConsumerName;

    /** Total rate of messages expired on this subscription (msg/s). */
    public double msgRateExpired;

    /** The count of expired messages on this subscription. */
    public long msgExpired;

    /** Total messages expired on this subscription. */
    public long totalMsgExpired;

    /** Last message expire execution timestamp. */
    public long lastExpireTimestamp;

    /** Last received consume flow command timestamp. */
    public long lastConsumedFlowTimestamp;

    /** Last consume message timestamp. */
    public long lastConsumedTimestamp;

    /** Last acked message timestamp. */
    public long lastAckedTimestamp;

    /** Last MarkDelete position advanced timestamp. */
    public long lastMarkDeleteAdvancedTimestamp;

    /** List of connected consumers on this subscription w/ their stats. */
    public List<ConsumerStatsImpl> consumers;

    /** Tells whether this subscription is durable or ephemeral (eg.: from a reader). */
    public boolean isDurable;

    /** Mark that the subscription state is kept in sync across different regions. */
    public boolean isReplicated;

    /** Whether out of order delivery is allowed on the Key_Shared subscription. */
    public boolean allowOutOfOrderDelivery;

    /** Whether the Key_Shared subscription mode is AUTO_SPLIT or STICKY. */
    public String keySharedMode;

    /** This is for Key_Shared subscription to get the recentJoinedConsumers in the Key_Shared subscription. */
    public Map<String, String> consumersAfterMarkDeletePosition;

    /**
     * For Key_Shared AUTO_SPLIT ordered subscriptions: The current number of hashes in the draining state.
     */
    public int drainingHashesCount;

    /**
     * For Key_Shared AUTO_SPLIT ordered subscriptions: The total number of hashes cleared from the draining state
     * for the connected consumers.
     */
    public long drainingHashesClearedTotal;

    /**
     * For Key_Shared AUTO_SPLIT ordered subscriptions: The total number of unacked messages for all draining hashes.
     */
    public int drainingHashesUnackedMessages;

    /** The number of non-contiguous deleted messages ranges. */
    public int nonContiguousDeletedMessagesRanges;

    /** The serialized size of non-contiguous deleted messages ranges. */
    public int nonContiguousDeletedMessagesRangesSerializedSize;

    /** The size of DelayedDeliveryTracer memory usage. */
    public long delayedMessageIndexSizeInBytes;

    @JsonIgnore
    public Map<String, TopicMetricBean> bucketDelayedIndexStats;

    /** SubscriptionProperties (key/value strings) associated with this subscribe. */
    public Map<String, String> subscriptionProperties;

    public long filterProcessedMsgCount;

    public long filterAcceptedMsgCount;

    public long filterRejectedMsgCount;

    public long filterRescheduledMsgCount;

    /** total number of times message dispatching was throttled on a subscription due to subscription rate limits. */
    public long dispatchThrottledMsgEventsBySubscriptionLimit;

    /** total number of times bytes dispatching was throttled on a subscription due to subscription rate limits. */
    public long dispatchThrottledBytesEventsBySubscriptionLimit;

    /** total number of times message dispatching was throttled on a subscription due to topic rate limits. */
    public long dispatchThrottledMsgEventsByTopicLimit;

    /** total number of times bytes dispatching was throttled on a subscription due to topic rate limits. */
    public long dispatchThrottledBytesEventsByTopicLimit;

    /** total number of times message dispatching was throttled on a subscription due to broker rate limits. */
    public long dispatchThrottledMsgEventsByBrokerLimit;

    /** total number of times bytes dispatching was throttled on a subscription due to broker rate limits. */
    public long dispatchThrottledBytesEventsByBrokerLimit;

    public SubscriptionStatsImpl() {
        this.consumers = new ArrayList<>();
        this.consumersAfterMarkDeletePosition = new LinkedHashMap<>();
        this.subscriptionProperties = new HashMap<>();
        this.bucketDelayedIndexStats = new HashMap<>();
    }

    public void reset() {
        msgRateOut = 0;
        msgThroughputOut = 0;
        bytesOutCounter = 0;
        msgOutCounter = 0;
        msgRateRedeliver = 0;
        messageAckRate = 0;
        chunkedMessageRate = 0;
        msgBacklog = 0;
        backlogSize = 0;
        msgBacklogNoDelayed = 0;
        msgDelayed = 0;
        msgInReplay = 0;
        unackedMessages = 0;
        type = null;
        msgRateExpired = 0;
        msgExpired = 0;
        totalMsgExpired = 0;
        lastExpireTimestamp = 0L;
        lastMarkDeleteAdvancedTimestamp = 0L;
        consumers.clear();
        consumersAfterMarkDeletePosition.clear();
        drainingHashesCount = 0;
        drainingHashesClearedTotal = 0L;
        drainingHashesUnackedMessages = 0;
        nonContiguousDeletedMessagesRanges = 0;
        nonContiguousDeletedMessagesRangesSerializedSize = 0;
        earliestMsgPublishTimeInBacklog = 0L;
        delayedMessageIndexSizeInBytes = 0;
        subscriptionProperties.clear();
        filterProcessedMsgCount = 0;
        filterAcceptedMsgCount = 0;
        filterRejectedMsgCount = 0;
        filterRescheduledMsgCount = 0;
        dispatchThrottledMsgEventsBySubscriptionLimit = 0;
        dispatchThrottledBytesEventsBySubscriptionLimit = 0;
        dispatchThrottledMsgEventsByBrokerLimit = 0;
        dispatchThrottledBytesEventsByBrokerLimit = 0;
        dispatchThrottledMsgEventsByTopicLimit = 0;
        dispatchThrottledBytesEventsByTopicLimit = 0;
        bucketDelayedIndexStats.clear();
    }

    // if the stats are added for the 1st time, we will need to make a copy of these stats and add it to the current
    // stats
    public SubscriptionStatsImpl add(SubscriptionStatsImpl stats) {
        Objects.requireNonNull(stats);
        this.msgRateOut += stats.msgRateOut;
        this.msgThroughputOut += stats.msgThroughputOut;
        this.bytesOutCounter += stats.bytesOutCounter;
        this.msgOutCounter += stats.msgOutCounter;
        this.msgRateRedeliver += stats.msgRateRedeliver;
        this.messageAckRate += stats.messageAckRate;
        this.chunkedMessageRate += stats.chunkedMessageRate;
        this.msgBacklog += stats.msgBacklog;
        this.backlogSize += stats.backlogSize;
        this.msgBacklogNoDelayed += stats.msgBacklogNoDelayed;
        this.msgDelayed += stats.msgDelayed;
        this.msgInReplay += stats.msgInReplay;
        this.unackedMessages += stats.unackedMessages;
        this.type = stats.type;
        this.msgRateExpired += stats.msgRateExpired;
        this.msgExpired += stats.msgExpired;
        this.totalMsgExpired += stats.totalMsgExpired;
        this.isReplicated |= stats.isReplicated;
        this.isDurable |= stats.isDurable;
        if (this.consumers.size() != stats.consumers.size()) {
            for (int i = 0; i < stats.consumers.size(); i++) {
                ConsumerStatsImpl consumerStats = new ConsumerStatsImpl();
                this.consumers.add(consumerStats.add(stats.consumers.get(i)));
            }
        } else {
            for (int i = 0; i < stats.consumers.size(); i++) {
                this.consumers.get(i).add(stats.consumers.get(i));
            }
        }
        this.allowOutOfOrderDelivery |= stats.allowOutOfOrderDelivery;
        this.consumersAfterMarkDeletePosition.putAll(stats.consumersAfterMarkDeletePosition);
        this.drainingHashesCount += stats.drainingHashesCount;
        this.drainingHashesClearedTotal += stats.drainingHashesClearedTotal;
        this.drainingHashesUnackedMessages += stats.drainingHashesUnackedMessages;
        this.nonContiguousDeletedMessagesRanges += stats.nonContiguousDeletedMessagesRanges;
        this.nonContiguousDeletedMessagesRangesSerializedSize += stats.nonContiguousDeletedMessagesRangesSerializedSize;
        if (this.earliestMsgPublishTimeInBacklog != 0 && stats.earliestMsgPublishTimeInBacklog != 0) {
            this.earliestMsgPublishTimeInBacklog = Math.min(
                    this.earliestMsgPublishTimeInBacklog,
                    stats.earliestMsgPublishTimeInBacklog
            );
        } else {
            this.earliestMsgPublishTimeInBacklog = Math.max(
                    this.earliestMsgPublishTimeInBacklog,
                    stats.earliestMsgPublishTimeInBacklog
            );
        }
        this.delayedMessageIndexSizeInBytes += stats.delayedMessageIndexSizeInBytes;
        this.subscriptionProperties.putAll(stats.subscriptionProperties);
        this.filterProcessedMsgCount += stats.filterProcessedMsgCount;
        this.filterAcceptedMsgCount += stats.filterAcceptedMsgCount;
        this.filterRejectedMsgCount += stats.filterRejectedMsgCount;
        this.filterRescheduledMsgCount += stats.filterRescheduledMsgCount;
        this.dispatchThrottledMsgEventsBySubscriptionLimit += stats.dispatchThrottledMsgEventsBySubscriptionLimit;
        this.dispatchThrottledBytesEventsBySubscriptionLimit += stats.dispatchThrottledBytesEventsBySubscriptionLimit;
        this.dispatchThrottledMsgEventsByBrokerLimit += stats.dispatchThrottledMsgEventsByBrokerLimit;
        this.dispatchThrottledBytesEventsByBrokerLimit += stats.dispatchThrottledBytesEventsByBrokerLimit;
        this.dispatchThrottledMsgEventsByTopicLimit += stats.dispatchThrottledMsgEventsByTopicLimit;
        this.dispatchThrottledBytesEventsByTopicLimit += stats.dispatchThrottledBytesEventsByTopicLimit;
        stats.bucketDelayedIndexStats.forEach((k, v) -> {
            TopicMetricBean topicMetricBean =
                    this.bucketDelayedIndexStats.computeIfAbsent(k, __ -> new TopicMetricBean());
            topicMetricBean.name = v.name;
            topicMetricBean.labelsAndValues = v.labelsAndValues;
            topicMetricBean.value += v.value;
        });

        return this;
    }
}
