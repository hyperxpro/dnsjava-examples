/*
 * Copyright 2023 Aayush Atharva
 *
 * Aayush Atharva licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.aayushatharva.oss.dnsjava.examples;

import com.aayushatharva.oss.dnsjava.examples.transport.UdpTransport;
import org.xbill.DNS.ClientSubnetOption;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Flags;
import org.xbill.DNS.Message;
import org.xbill.DNS.Name;
import org.xbill.DNS.OPTRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Section;
import org.xbill.DNS.Type;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * <h1> EDNS Client Subnet </h1>
 * <a href="https://datatracker.ietf.org/doc/html/rfc7871">RFC 7871</a>
 *
 */
public final class ECSExample {

    public static void main(String[] args) throws IOException {
        // Create a new ClientSubnetOption with /24 mask
        ClientSubnetOption clientSubnetOption = new ClientSubnetOption(24, 24, InetAddress.getByName("3.6.21.0"));

        // Craft a new query message
        Message message = Message.newQuery(Record.newRecord(Name.fromString("www.shieldblaze.com."), Type.A, DClass.IN));
        message.getHeader().setFlag(Flags.RD);
        message.addRecord(new OPTRecord(2048, 0, 0, 0, clientSubnetOption), Section.ADDITIONAL);

        // Send and receive DNS message from resolver
        byte[] response = UdpTransport.sendAndReceive(new InetSocketAddress("8.8.8.8", 53), message.toWire());

        // Print
        System.out.println("--------------------------- RESPONSE ---------------------------");
        System.out.println(new Message(response));
        System.out.println("----------------------------------------------------------------");
    }
}
