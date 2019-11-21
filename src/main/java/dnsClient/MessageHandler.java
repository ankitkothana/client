
package dnsClient;

import java.util.concurrent.atomic.AtomicInteger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MessageHandler extends ChannelInboundHandlerAdapter {
    
    public static AtomicInteger count = new AtomicInteger();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        count.incrementAndGet();
    }
}
