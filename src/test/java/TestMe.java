import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.example.web.thrift.userpurse.api.IUserPurseService;
import org.example.web.thrift.userpurse.api.UserPurseBo;

import java.util.Objects;

public class TestMe {

    public static void main(String[] args) {
        int i = 121;
        long j = 121L;
        System.out.println(i == j);

    }


}
