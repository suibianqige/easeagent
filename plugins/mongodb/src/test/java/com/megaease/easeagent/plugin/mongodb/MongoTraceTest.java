/*
 * Copyright (c) 2021 MegaEase
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.megaease.easeagent.plugin.mongodb;

import com.megaease.easeagent.mock.plugin.api.MockEaseAgent;
import com.megaease.easeagent.plugin.mongodb.interceptor.listener.MongoTraceCommandListener;
import com.mongodb.event.CommandFailedEvent;
import com.mongodb.event.CommandSucceededEvent;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@MockEaseAgent
public class MongoTraceTest extends MongoBaseTest {
    MongoTraceCommandListener listener;

    @Before
    public void before() {
        super.before();
        listener = new MongoTraceCommandListener(this.config);
    }

    @Test
    public void performSuccess() {
        BsonDocument bsonDocument = new BsonDocument();
        bsonDocument.put("collection", new BsonString(collection));
        CommandSucceededEvent succeededEvent = new CommandSucceededEvent(this.requestId, this.connectionDescription, this.cmdName, bsonDocument, 10);
        this.listener.commandStarted(startedEvent);
        this.listener.commandSucceeded(succeededEvent);
        this.assertTrace(true, null);
    }

    @Test
    public void performOpFail() {
        BsonDocument errDoc = new BsonDocument();
        errDoc.put("errmsg", new BsonString(errMsg));
        List<BsonValue> list = new ArrayList<>();
        list.add(errDoc);
        BsonArray bsonValues = new BsonArray(list);
        BsonDocument bsonDocument = new BsonDocument();
        bsonDocument.put("collection", new BsonString(collection));
        bsonDocument.put("writeErrors", bsonValues);
        CommandSucceededEvent succeededEvent = new CommandSucceededEvent(this.requestId, this.connectionDescription, this.cmdName, bsonDocument, 10);
        this.listener.commandStarted(startedEvent);
        this.listener.commandSucceeded(succeededEvent);
        this.assertTrace(false, errMsg);
    }

    @Test
    public void performOpFail2() {
        CommandFailedEvent failedEvent = new CommandFailedEvent(this.requestId, this.connectionDescription, this.cmdName, 10, new RuntimeException(this.errMsg));
        this.listener.commandStarted(startedEvent);
        this.listener.commandFailed(failedEvent);
        this.assertTrace(false, errMsg);
    }
}
