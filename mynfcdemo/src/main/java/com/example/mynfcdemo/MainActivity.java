package com.example.mynfcdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /** 相当于一个NFC适配器，类似于电脑装了网络适配器才能上网，手机装了NfcAdapter才能发起NFC通信 */
    private NfcAdapter nfcAdapter;
    private TextView info;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.nfc_info);
        /** 由于大部分Android设备只支持一个NFC Adapter，所以一般直接调用getDefaultAapater来获取手机中的Adapter */
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            info.setText("此设备不支持NFC！");
            return;
        }
        /** NFC功能未启用 */
        if (!nfcAdapter.isEnabled()) {
            info.setText("请在系统设置中先启用NFC功能！");
            return;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        /** 得到是否检测到ACTION_TECH_DISCOVERED触发 */
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
            /** 处理该intent */
            processIntent(getIntent());
        }
    }
    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("0x");
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            /** 确定为指定基数中的一个特定的数字的字符表示。如果该值的基数不是有效的基数，或值的数字是不指定基数中的有效数字，则返回空字符（'\ u0000的） */
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            System.out.println(buffer);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }

    /** 从intent中解析NDEF消息，打印到textview中 */
    private void processIntent(Intent intent) {
        /** 取出封装在intent中的TAG */
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        for (String tech : tagFromIntent.getTechList()) {
            System.out.println(tech);
        }
        boolean auth = false;
        /** 读取TAG */
        MifareClassic mfc = MifareClassic.get(tagFromIntent);
        try {
            String metaInfo = "";
            /** TagTechnology对象中启用I/O操作 */
            mfc.connect();
            /** 获取TAG类型 */
            int type = mfc.getType();
            /** 获取TAG包含的扇区数 */
            int sectorCount = mfc.getSectorCount();
            String typeS = "";
            switch (type) {
                case MifareClassic.TYPE_CLASSIC:
                    typeS = "TYPE_CLASSIC";
                    break;
                case MifareClassic.TYPE_PLUS:
                    typeS = "TYPE_PLUS";
                    break;
                case MifareClassic.TYPE_UNKNOWN:
                    typeS = "TYPE_UNKNOWN";
                    break;

                case MifareClassic.TYPE_PRO:
                    typeS = "TYPE_PRO";
                    break;

                default:

                    break;
            }
            metaInfo += "卡片类型：" + typeS + "\n共" + sectorCount + "个扇区\n共"
                    + mfc.getBlockCount() + "个块\n存储空间: " + mfc.getSize() + "B\n";
            for (int j = 0; j < sectorCount; j++) {
                /** 块通过密码进行身份认证 */
                auth = mfc.authenticateSectorWithKeyA(j,
                        MifareClassic.KEY_DEFAULT);
                int bCount;
                int bIndex;
                if (auth) {
                    metaInfo += "Sector " + j + ":验证成功\n";
                    /** 读取扇区中的块 */
                    bCount = mfc.getBlockCountInSector(j);
                    bIndex = mfc.sectorToBlock(j);
                    for (int i = 0; i < bCount; i++) {
                        /** 读取块的信息 */
                        byte[] data = mfc.readBlock(bIndex);
                        metaInfo += "Block " + bIndex + " : "
                                + bytesToHexString(data) + "\n";
                        bIndex++;
                    }
                } else {
                    metaInfo += "Sector " + j + ":验证失败\n";
                }
            }
            info.setText(metaInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
