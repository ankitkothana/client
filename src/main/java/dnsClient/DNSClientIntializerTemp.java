
package dnsClient;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DatagramPacketDecoder;
import io.netty.handler.codec.DatagramPacketEncoder;
import io.netty.channel.socket.DatagramChannel;

public class DNSClientIntializerTemp extends ChannelInitializer<DatagramChannel> {
    
    @Override
    protected void initChannel(DatagramChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast("MessageDecoder",
                new DatagramPacketDecoder(new DNSDecoder()));
        channelPipeline.addLast("MessageEncoder",
                new DatagramPacketEncoder<>(new DNSEncoder()));
        channelPipeline.addLast("MesageHandler", new MessageHandler());
    }
    
}
