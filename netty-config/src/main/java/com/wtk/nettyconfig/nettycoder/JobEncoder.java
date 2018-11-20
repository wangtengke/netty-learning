package com.wtk.nettyconfig.nettycoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * Job编码
 */
public class JobEncoder extends MessageToByteEncoder<Job> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Job job, ByteBuf out) throws Exception {
        Schema<Job> schema = RuntimeSchema.createFrom(Job.class);
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte[] data = ProtostuffIOUtil.toByteArray(job, schema, buffer);
        out.writeBytes(data);
    }
}
