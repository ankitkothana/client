
package dnsClient;

import java.util.List;

import org.xbill.DNS.Message;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class DNSEncoder extends MessageToMessageEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        out.add(Unpooled.copiedBuffer(msg.toWire()));
    }



}
