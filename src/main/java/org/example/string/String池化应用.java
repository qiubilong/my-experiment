package org.example.string;

import com.sun.management.HotSpotDiagnosticMXBean;

import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Random;

/**
 * @author chenxuegui
 * @since 2023/10/24
 */
public class String池化应用 {

    static final int MAX = 1000 * 10000;
    static final String[] arr = new String[MAX];

    public static void main(String[] args) throws Exception {
        Integer[] DB_DATA = new Integer[10];
        Random random = new Random(10 * 10000);
        for (int i = 0; i < DB_DATA.length; i++) {
            DB_DATA[i] = random.nextInt();
        }
        long t = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            arr[i] = new String(String.valueOf(DB_DATA[i % DB_DATA.length]));
            //arr[i] = new String(String.valueOf(DB_DATA[i % DB_DATA.length])).intern();
        }

        System.out.println((System.currentTimeMillis() - t) + "ms");
        dumpHeap();
    }

    public static void dumpHeap() {
        try {
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            HotSpotDiagnosticMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(
                    server, "com.sun.management:type=HotSpotDiagnostic", HotSpotDiagnosticMXBean.class);
            mxBean.dumpHeap("heapDump1"+".hprof", true);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
