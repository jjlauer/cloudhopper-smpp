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

package com.cloudhopper.smpp.impl;

import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.SmppSessionHandler;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.type.RecoverablePduException;
import com.cloudhopper.smpp.type.UnrecoverablePduException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation that provides empty implementations of all required
 * methods.  Users are free to subclass this class to only override the methods
 * they require to specialize.
 * 
 * @author joelauer
 */
public class DefaultSmppSessionHandler implements SmppSessionHandler {
    private final Logger logger;

    public DefaultSmppSessionHandler() {
        this(LoggerFactory.getLogger(DefaultSmppSessionHandler.class));
    }

    public DefaultSmppSessionHandler(Logger logger) {
        this.logger = logger;
    }

    public String lookupResultMessage(int commandStatus) {
        return null;
    }

    public String lookupTlvTagName(short tag) {
        return null;
    }

    public void fireChannelUnexpectedlyClosed() {
        logger.info("Default handling is to discard an unexpected channel closed");
    }

    public PduResponse firePduRequestReceived(PduRequest pduRequest) {
        logger.warn("Default handling is to discard unexpected request PDU: {}", pduRequest);
        return null;
    }

    public void fireExpectedPduResponseReceived(PduAsyncResponse pduAsyncResponse) {
        logger.warn("Default handling is to discard expected response PDU: {}", pduAsyncResponse);
    }

    public void fireUnexpectedPduResponseReceived(PduResponse pduResponse) {
        logger.warn("Default handling is to discard unexpected response PDU: {}", pduResponse);
    }

    public void fireUnrecoverablePduException(UnrecoverablePduException e) {
        logger.warn("Default handling is to discard a unrecoverable exception: {}", e);
    }

    public void fireRecoverablePduException(RecoverablePduException e) {
        logger.warn("Default handling is to discard a recoverable exception: {}", e);
    }

    public void fireUnknownThrowable(Throwable t) {
        logger.warn("Default handling is to discard an unknown throwable: {}", t);
    }

    public void firePduRequestExpired(PduRequest pduRequest) {
        logger.warn("Default handling is to discard expired request PDU: {}", pduRequest);
    }
    
}
