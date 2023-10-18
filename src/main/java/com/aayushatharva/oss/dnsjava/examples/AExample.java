package com.aayushatharva.oss.dnsjava.examples;

import com.aayushatharva.oss.dnsjava.examples.transport.UdpTransport;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Flags;
import org.xbill.DNS.Message;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 *
 * <h1> A Record DNS Query </h1>
 */
public final class AExample {

    public static void main(String[] args) throws IOException {
        // Craft a new query message
        Message message = Message.newQuery(Record.newRecord(Name.fromString("www.shieldblaze.com."), Type.A, DClass.IN));
        message.getHeader().setFlag(Flags.RD);

        // Send and receive DNS message from resolver
        byte[] response = UdpTransport.sendAndReceive(new InetSocketAddress("8.8.8.8", 53), message.toWire());

        // Print
        System.out.println("--------------------------- RESPONSE ---------------------------");
        System.out.println(new Message(response));
        System.out.println("----------------------------------------------------------------");
    }
}
