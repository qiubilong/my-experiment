package org.example.netty.nio.epoll;

import java.io.IOException;
import java.nio.channels.*;
import java.nio.channels.spi.*;
import java.util.*;
import sun.misc.*;
import sun.nio.ch.IOUtil;
import sun.nio.ch.SelChImpl;
import sun.nio.ch.SelectionKeyImpl;
import sun.nio.ch.SelectorImpl;

/**
 * An implementation of Selector for Linux 2.6+ kernels that uses
 * the epoll event notification facility.
 */
/*
class EPollSelectorImpl extends SelectorImpl {

    // File descriptors used for interrupt
    protected int fd0;
    protected int fd1;

    // The poll object
    EPollArrayWrapper pollWrapper;

    // Maps from file descriptors to keys
    private Map<Integer, SelectionKeyImpl> fdToKey;    //socket文件描述符fd  <--> SelectionKeyImpl

    // True if this Selector has been closed
    private volatile boolean closed = false;

    // Lock for interrupt triggering and clearing
    private final Object interruptLock = new Object();
    private boolean interruptTriggered = false;

    */
/**
     * Package private constructor called by factory method in
     * the abstract superclass Selector.
     *//*

    EPollSelectorImpl(SelectorProvider sp) throws IOException {
        super(sp);
        long pipeFds = IOUtil.makePipe(false);
        fd0 = (int) (pipeFds >>> 32);
        fd1 = (int) pipeFds;
        pollWrapper = new EPollArrayWrapper();
        pollWrapper.initInterrupt(fd0, fd1);
        fdToKey = new HashMap<>();
    }

    protected int doSelect(long timeout) throws IOException {
        if (closed)
            throw new ClosedSelectorException();
        processDeregisterQueue();
        try {
            begin();
            pollWrapper.poll(timeout);                              //等待IO事件发生
        } finally {
            end();
        }
        processDeregisterQueue();
        int numKeysUpdated = updateSelectedKeys();                  //将有IO事件发生的socket，添加到SelectedKeys集合
        if (pollWrapper.interrupted()) {
            // Clear the wakeup pipe
            pollWrapper.putEventOps(pollWrapper.interruptedIndex(), 0);
            synchronized (interruptLock) {
                pollWrapper.clearInterrupted();
                IOUtil.drain(fd0);
                interruptTriggered = false;
            }
        }
        return numKeysUpdated;
    }

    */
/**
     * Update the keys whose fd's have been selected by the epoll.
     * Add the ready keys to the ready queue.
     *//*

    private int updateSelectedKeys() {
        int entries = pollWrapper.updated;
        int numKeysUpdated = 0;
        for (int i=0; i<entries; i++) {
            int nextFD = pollWrapper.getDescriptor(i);
            SelectionKeyImpl ski = fdToKey.get(Integer.valueOf(nextFD));
            // ski is null in the case of an interrupt
            if (ski != null) {
                int rOps = pollWrapper.getEventOps(i);
                if (selectedKeys.contains(ski)) {
                    if (ski.channel.translateAndSetReadyOps(rOps, ski)) {
                        numKeysUpdated++;
                    }
                } else {
                    ski.channel.translateAndSetReadyOps(rOps, ski);
                    if ((ski.nioReadyOps() & ski.nioInterestOps()) != 0) {
                        selectedKeys.add(ski);
                        numKeysUpdated++;
                    }
                }
            }
        }
        return numKeysUpdated;
    }

    protected void implClose() throws IOException {
        if (closed)
            return;
        closed = true;

        // prevent further wakeup
        synchronized (interruptLock) {
            interruptTriggered = true;
        }

        FileDispatcherImpl.closeIntFD(fd0);
        FileDispatcherImpl.closeIntFD(fd1);

        pollWrapper.closeEPollFD();
        // it is possible
        selectedKeys = null;

        // Deregister channels
        Iterator<SelectionKey> i = keys.iterator();
        while (i.hasNext()) {
            SelectionKeyImpl ski = (SelectionKeyImpl)i.next();
            deregister(ski);
            SelectableChannel selch = ski.channel();
            if (!selch.isOpen() && !selch.isRegistered())
                ((SelChImpl)selch).kill();
            i.remove();
        }

        fd0 = -1;
        fd1 = -1;
    }

    protected void implRegister(SelectionKeyImpl ski) {
        if (closed)
            throw new ClosedSelectorException();
        SelChImpl ch = ski.channel;
        int fd = Integer.valueOf(ch.getFDVal());
        fdToKey.put(fd, ski);
        pollWrapper.add(fd);
        keys.add(ski);
    }

    protected void implDereg(SelectionKeyImpl ski) throws IOException {
        assert (ski.getIndex() >= 0);
        SelChImpl ch = ski.channel;
        int fd = ch.getFDVal();
        fdToKey.remove(Integer.valueOf(fd));
        pollWrapper.remove(fd);
        ski.setIndex(-1);
        keys.remove(ski);
        selectedKeys.remove(ski);
        deregister((AbstractSelectionKey)ski);
        SelectableChannel selch = ski.channel();
        if (!selch.isOpen() && !selch.isRegistered())
            ((SelChImpl)selch).kill();
    }

    public void putEventOps(SelectionKeyImpl ski, int ops) {
        if (closed)
            throw new ClosedSelectorException();
        SelChImpl ch = ski.channel;
        pollWrapper.setInterest(ch.getFDVal(), ops);
    }

    public Selector wakeup() {
        synchronized (interruptLock) {
            if (!interruptTriggered) {
                pollWrapper.interrupt();
                interruptTriggered = true;
            }
        }
        return this;
    }
}*/
