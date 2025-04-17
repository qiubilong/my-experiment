package org.example.netty.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.example.netty.netty.common.RpcMessage;

import java.util.List;

/**
 * @author chenxuegui
 * @since 2025/4/17
 *
 * TCP字节流 - 解码分包
 */
@Slf4j
public class RpcMessageDecoder extends ByteToMessageDecoder {
    int length;
    int type;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("RpcMessageDecoder ByteBuf={}",in);
        if(in.readableBytes()>8){
            if(this.length ==0){
                this.length = in.readInt() - 4;
                this.type = in.readInt();
            }

            if(in.readableBytes()<length){
                log.info("RpcMessageDecoder length={},ByteBuf={}",length,in);
                return;
            }

            byte[] content = new byte[length];
            in.readBytes(content);
            if(this.type == 1){
                log.info("RpcMessageDecoder inner type={},message={}",new String(content),type);
            }else {
                RpcMessage rpcMessage = new RpcMessage() ;
                rpcMessage.setLen(length).setType(type).setContent(content);
                log.info("RpcMessageDecoder rpc message={}",rpcMessage);
                out.add(rpcMessage);/* 传递到下一个Handler */
            }

            this.type = 0;
            this.length = 0;
        }
    }
}
