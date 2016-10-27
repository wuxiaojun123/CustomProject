package com.wxj.customview.waterfallFlow;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.wxj.customview.R;
import com.wxj.customview.utils.ImageLoader;
import com.wxj.customview.utils.Images;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wuxiaojun on 16-7-23.
 */
public class WaterFallFlowView extends ScrollView implements View.OnTouchListener {
    //每一页加载的数量
    public static final int PAGE_SIZE = 15;
    //记录当前已加载到第几页
    private int page;
    //每一列的宽度
    private int columWidth;
    //当前第一列的高度
    private int firstColumnHeight;
    //当前第二列的高度
    private int secondColumnHeight;
    //当前第三列的高度
    private int thirdColumnHeight;
    //是否已加载过一次layout，这里onLayout中的初始化只需要加载一次
    private boolean loadOnce;
    //对图片进行管理的工具类
    private ImageLoader imageloader;
    //第一列的布局
    private LinearLayout firstColumn;
    //第二列的布局
    private LinearLayout secondColumn;
    //第三列的布局
    private LinearLayout thirdColumn;
    //记录所有正在下载或等待下载的任务
    private static Set<LoadImageTask> taskCollection;
    //MyScrollView下的直接子布局
    private static View scrollLayout;
    //MyScrollView布局的高度
    private static int scrollViewHeight;
    //记录上垂直方向的滚动距离
    private static int lastScrollY = -1;
    //记录所有界面上的图片，用以可以随时对图片的释放
    private List<ImageView> imageViewList = new ArrayList<>();


    private static Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            WaterFallFlowView myScrollView = (WaterFallFlowView) msg.obj;
            int scrollY = myScrollView.getScrollY();
            //如果当前的滚动位置和上次相同，表示已停止滚动
            if (scrollY == lastScrollY) {
                //当滚动的最底部，并且当前没有正在下载的任务时
                //开始加载下一页的图片
                if (scrollViewHeight + scrollY >= scrollLayout.getHeight()
                        && taskCollection.isEmpty()) {
                    myScrollView.loadMoreImages();
                }
                myScrollView.checkVisibility();
            }else{
                lastScrollY = scrollY;
                Message msg2 = handler.obtainMessage();
                msg2.obj = myScrollView;
                handler.sendMessageDelayed(msg2,5);
            }
        }
    };


    public WaterFallFlowView(Context context) {
        this(context, null);
    }

    public WaterFallFlowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterFallFlowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        imageloader = ImageLoader.getInstance();
        taskCollection = new HashSet<>();
        setOnTouchListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed && !loadOnce){
            scrollViewHeight = getHeight();
            scrollLayout = getChildAt(0);
            firstColumn = (LinearLayout) findViewById(R.id.ll_1);
            secondColumn = (LinearLayout) findViewById(R.id.ll_2);
            thirdColumn = (LinearLayout) findViewById(R.id.ll_3);
            columWidth = firstColumn.getWidth();
            loadOnce = true;
            loadMoreImages();;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            Message msg = new Message();
            msg.obj = this;
            handler.sendMessageDelayed(msg, 5);
        }
        return false;
    }

    private boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /***
     * 开始加载下一页的图片，每张图片都会开启一个异步线程去下载
     *
     */
    public void loadMoreImages(){
        if(hasSDCard()){
            int startIndex = page * PAGE_SIZE;
            int endIndex = page * PAGE_SIZE + PAGE_SIZE;
            if(startIndex < Images.imageUrls.length){
                Toast.makeText(getContext(),"正在加载...",Toast.LENGTH_SHORT).show();
                if(endIndex > Images.imageUrls.length){
                    endIndex = Images.imageUrls.length;
                }
                for (int i = startIndex; i < endIndex; i++) {
                    LoadImageTask task = new LoadImageTask();
                    taskCollection.add(task);
                    task.execute(Images.imageUrls[i]);
                }
                page++;
            }else{
                Toast.makeText(getContext(), "已没有更多图片", Toast.LENGTH_SHORT)
                        .show();
            }
        }else{
            Toast.makeText(getContext(), "未发现SD卡", Toast.LENGTH_SHORT).show();
        }
    }


    public void checkVisibility(){

        for (int i = 0; i < imageViewList.size(); i++) {
            ImageView imageView = imageViewList.get(i);
            int borderTop = (int) imageView.getTag(R.string.border_top);
            int borderBottom = (int) imageView.getTag(R.string.border_bottom);
            if(borderBottom > getScrollY()
                    && borderTop < getScrollY()+scrollViewHeight){
                String imageUrl = (String) imageView.getTag(R.string.image_url);
                Bitmap bitmap = imageloader.getBitmapFromMemoryCache(imageUrl);
                if(bitmap != null){
                    imageView.setImageBitmap(bitmap);
                }else{
                    LoadImageTask task = new LoadImageTask(imageView);
                    task.execute(imageUrl);
                }
            }else{
                imageView.setImageResource(R.drawable.empty_photo);
            }

        }


    }




    class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        //图片地址
        private String mImageUrl;
        //可重复使用的imageview
        private ImageView mImageView;

        public LoadImageTask() {
        }

        public LoadImageTask(ImageView imageView) {
            mImageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            mImageUrl = params[0];
            Bitmap imageBitmap = imageloader.getBitmapFromMemoryCache(mImageUrl);
            if (imageBitmap == null) {
                //加载图片
                try {
                    imageBitmap = loadImage(mImageUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return imageBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null){
                double ratio = bitmap.getWidth()/(columWidth*1.0);
                int scaledHeight = (int) (bitmap.getHeight()/ratio);
                addImage(bitmap, columWidth, scaledHeight);
            }
            taskCollection.remove(this);
        }

        /***
         * 添加图片
         * @param bitmap
         * @param imageWidth
         * @param imageHeight
         */
        private void addImage(Bitmap bitmap, int imageWidth, int imageHeight) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    imageWidth,imageHeight);
            if(mImageView != null){
                mImageView.setImageBitmap(bitmap);
            }else{
                ImageView imageView = new ImageView(getContext());
                imageView.setLayoutParams(params);
                imageView.setImageBitmap(bitmap);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(5, 5, 5, 5);
                imageView.setTag(R.string.image_url, mImageUrl);
                findColumnToAdd(imageView,imageHeight).addView(imageView);
                imageViewList.add(imageView);
            }

        }

        /***
         * 找到此时应该添加图片的一列
         * 原则就是对三列的高度进行判断，当前高度最小的一列就是应该添加的一列
         * @param imageView
         * @param imageHeight
         * @return
         */
        private LinearLayout findColumnToAdd(ImageView imageView, int imageHeight) {
            if(firstColumnHeight <= secondColumnHeight){
                if(firstColumnHeight <= thirdColumnHeight){
                    imageView.setTag(R.string.border_top,firstColumnHeight);
                    firstColumnHeight += imageHeight;
                    imageView.setTag(R.string.border_bottom,firstColumnHeight);
                    return firstColumn;
                }
                imageView.setTag(R.string.border_top,thirdColumnHeight);
                thirdColumnHeight += imageHeight;
                imageView.setTag(R.string.border_bottom,thirdColumnHeight);
                return thirdColumn;
            }else{
                if(secondColumnHeight <= thirdColumnHeight){
                    imageView.setTag(R.string.border_top,secondColumnHeight);
                    secondColumnHeight += imageHeight;
                    imageView.setTag(R.string.border_bottom,secondColumnHeight);
                    return secondColumn;
                }
                imageView.setTag(R.string.border_top,thirdColumnHeight);
                thirdColumnHeight += imageHeight;
                imageView.setTag(R.string.border_bottom,thirdColumnHeight);
                return thirdColumn;
            }
        }

        /***
         * 先判断图片释放存在SDCard中，如果不存在，则充网络上加载
         *
         * @param mImageUrl
         * @return
         */
        private Bitmap loadImage(String mImageUrl) throws IOException {
            File mFile = new File(getImagePath(mImageUrl));
            if(!mFile.exists()){
                downloadImage(mImageUrl);
            }
            if(mImageUrl != null){
                Bitmap bitmap = ImageLoader.decodeSampleBitmapFromResource(mFile.getPath(), columWidth);
                if(bitmap != null){
                    imageloader.addBitmapToMemoryCache(mImageUrl,bitmap);
                    return bitmap;
                }
            }
            return null;
        }


    }


    /***
     * 下载图片，并且保存到lrucache和文件中
     * @param mImageUrl
     */
    private void downloadImage(String mImageUrl) throws IOException {
        HttpURLConnection con = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        File imageFile = null;
        try{
            URL url = new URL(mImageUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(10000);
            con.setReadTimeout(15 * 1000);
            con.setDoOutput(true);
            con.setDoInput(true);
            bis = new BufferedInputStream(con.getInputStream());
            imageFile = new File(getImagePath(mImageUrl));
            fos = new FileOutputStream(imageFile);
            bos = new BufferedOutputStream(fos);
            byte[] b = new byte[1024*5];
            int length = -1;
            while((length = bis.read(b)) != -1){
                bos.write(b,0,length);
                bos.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(bis != null){
                    bis.close();
                }
                if(bos != null){
                    bos.close();
                }
                if(fos != null){
                    fos.close();
                }
                if(con != null){
                    con.disconnect();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(imageFile != null){
            Bitmap bitmap = ImageLoader.decodeSampleBitmapFromResource(imageFile.getPath(), columWidth);
            if(bitmap != null){
                imageloader.addBitmapToMemoryCache(mImageUrl,bitmap);
            }
        }
    }


    /***
     * 获取图片路径
     *
     * @param mImageUrl
     * @return
     */
    private String getImagePath(String mImageUrl) {
        int mlastIndex = mImageUrl.lastIndexOf("/");
        String imgName = mImageUrl.substring(mlastIndex + 1);
        String imgDir = Environment.getExternalStorageDirectory().getPath() + "/waterfall/";
        File mFile = new File(imgDir);
        if (!mFile.exists()) {
            mFile.mkdirs();
        }
        String imagePath = imgDir + imgName;
        return imagePath;
    }


}
