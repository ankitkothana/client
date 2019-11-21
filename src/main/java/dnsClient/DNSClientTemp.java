package dnsClient;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import javax.annotation.PostConstruct;

import org.xbill.DNS.DClass;
import org.xbill.DNS.Message;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultAddressedEnvelope;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class DNSClientTemp {

    /**
     * Default UDP buffer size.
     */
    private static final int UDP_BUF_SIZE = 9216;

    private Bootstrap bootstrap;

   private Channel channel;

    @PostConstruct
    public void setUp() throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioDatagramChannel.class);

        bootstrap.option(ChannelOption.SO_RCVBUF, UDP_BUF_SIZE);
        bootstrap.option(ChannelOption.SO_SNDBUF, UDP_BUF_SIZE);
        bootstrap.option(ChannelOption.SO_BROADCAST, true);
        bootstrap.option(ChannelOption.SO_REUSEADDR, true);

        bootstrap.handler(new DNSClientIntializerTemp());

        channel = bootstrap.bind(new InetSocketAddress(0)).channel();
        
    }

    public void sendUDP(Message message, InetSocketAddress host) throws InterruptedException {
        //Channel channel = bootstrap.bind(new InetSocketAddress(0)).channel();
        channel.writeAndFlush(new DefaultAddressedEnvelope<Message, SocketAddress>(message, host));

        // System.out.println("I am here");

        // write.sync();
    }

    public static void main(String[] args) throws TextParseException, InterruptedException {
        DNSClientTemp dnsClient = new DNSClientTemp();

        dnsClient.setUp();

        try {
            for (int i = 1000; i < 20000; i++) {
                Record soaRec = Record.newRecord(new Name("google.com."), Type.SOA, DClass.IN);
                Message mesg = Message.newQuery(soaRec);
                dnsClient.sendUDP(mesg, new InetSocketAddress("ns3.google.com.", 53));
            }

            Thread.sleep(5000);

            System.out.println(MessageHandler.count.toString());

            Thread.sleep(5000);

            System.out.println(MessageHandler.count.toString());

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
