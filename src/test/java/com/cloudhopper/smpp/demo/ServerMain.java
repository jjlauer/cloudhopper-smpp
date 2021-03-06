/**
 * Copyright (C) 2011 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.cloudhopper.smpp.demo;

import com.cloudhopper.smpp.SmppServerConfiguration;
import com.cloudhopper.smpp.SmppServerHandler;
import com.cloudhopper.smpp.SmppServerSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppServer;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.BaseBind;
import com.cloudhopper.smpp.pdu.BaseBindResp;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.type.SmppProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author joelauer
 */
public class ServerMain {
    private static final Logger logger = LoggerFactory.getLogger(ServerMain.class);

    static public void main(String[] args) throws Exception {
        SmppServerConfiguration configuration = new SmppServerConfiguration();
        configuration.setPort(2776);
        
        DefaultSmppServer smppServer = new DefaultSmppServer(configuration, new DefaultSmppServerHandler());

        logger.info("About to start SMPP server");
        smppServer.start();
        logger.info("SMPP server started");

        System.out.println("Press any key to stop server");
        System.in.read();

        logger.info("SMPP server stopping");
        smppServer.stop();
        logger.info("SMPP server stopped");
    }

    public static class DefaultSmppServerHandler implements SmppServerHandler {

        public void sessionBindRequested(Long sessionId, SmppSessionConfiguration sessionConfiguration, final BaseBind bindRequest) throws SmppProcessingException {

            // test name change of sessions
            sessionConfiguration.setName("Application.SMPP." + sessionConfiguration.getSystemId() + "." + sessionConfiguration.getSystemType());

            //throw new SmppProcessingException(SmppConstants.STATUS_BINDFAIL, null);
        }

        public void sessionCreated(Long sessionId, SmppServerSession session, BaseBindResp preparedBindResponse) throws SmppProcessingException {
            logger.info("Session created: {}", session);
            // need to do something it now (flag we're ready)
            session.serverReady(new TestSmppSessionHandler());
        }

        public void sessionDestroyed(Long sessionId, SmppServerSession session) {
            logger.info("Session destroyed: {}", session);
        }

    }

    public static class TestSmppSessionHandler extends DefaultSmppSessionHandler {
        @Override
        public PduResponse firePduRequestReceived(PduRequest pduRequest) {
            // ignore for now (already logged)
            return pduRequest.createResponse();
        }
    }
    
}
