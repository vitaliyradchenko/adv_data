package prac.job;

/**
 * Created by vitaliyradchenko on 12/27/16.
 */
import java.io.IOException;
import java.util.UUID;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import prac.UuidAdapter;

public class MCMapper extends TableMapper<Text, IntWritable> {

    private final IntWritable ONE = new IntWritable(1);

    private Text text = new Text();

    public void map(ImmutableBytesWritable row, Result value, Context context) throws IOException, InterruptedException {

        ImmutableBytesWritable patientKey = new ImmutableBytesWritable(row.get(), 0, 2*Bytes.SIZEOF_BYTE);
        UUID patientId = UuidAdapter.getUUIDFromBytes(patientKey.get());

        byte[] type = value.getValue(Bytes.toBytes("medical_records"), Bytes.toBytes("type"));
        String val = patientId + " " + " - " + new String(type);
        text.set(val);
        context.write(text, ONE);
    }
}
