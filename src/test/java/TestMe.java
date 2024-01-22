import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.example.web.thrift.userpurse.api.IUserPurseService;
import org.example.web.thrift.userpurse.api.UserPurseBo;

public class TestMe {

    public static void main(String[] args) {
        try (TTransport transport = new TSocket("localhost", 9090)) {
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            IUserPurseService.Client userPurseService = new IUserPurseService.Client(protocol);

            UserPurseBo user = userPurseService.getUserPurseInfo(200000010L);
            System.out.println(user);

            UserPurseBo user1 = userPurseService.getUserPurseInfo(200000010L);
            System.out.println(user1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
