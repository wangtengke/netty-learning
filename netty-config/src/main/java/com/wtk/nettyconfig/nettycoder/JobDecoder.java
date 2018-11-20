package com.wtk.nettyconfig.nettycoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.List;

/**
 * @program: netty
 * @description: 任务object解码 802611496430044097
 * @author: WangTengKe
 * @create: 2018-11-15
 **/
public class JobDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] data = new byte[in.readableBytes()];
        in.readBytes(data);
        Job job = Job.class.newInstance();
        Schema<Job> schema = RuntimeSchema.getSchema(Job.class);
        ProtostuffIOUtil.mergeFrom(data, job, schema);
        out.add(job);
    }
}
