package org.example.netty.nio.epoll;

import sun.nio.ch.SelectorProviderImpl;

import java.io.IOException;
import java.nio.channels.*;
import java.nio.channels.spi.*;
/*

public class EPollSelectorProvider extends SelectorProviderImpl {
    public AbstractSelector openSelector() throws IOException {
        return new EPollSelectorImpl(this);
    }

    public Channel inheritedChannel() throws IOException {
        return InheritedChannel.getChannel();
    }
}
*/
