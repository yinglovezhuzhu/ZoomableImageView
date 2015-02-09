/*
 * Copyright (C) $today.year The Android Open Source Project.
 *
 *	yinglovezhuzhu@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.opensource.zoomableimageview;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.opensource.bitmaploader.ImageCache;
import com.opensource.bitmaploader.ImageFetcher;
import com.opensource.bitmaploader.ImageWorker;
import com.opensource.bitmaploader.Utils;
import com.opensource.widget.ZoomableImageView;

public class NetSampleActivity extends Activity {

    private ImageFetcher mImageFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_simple);

        ZoomableImageView photoView = (ZoomableImageView) findViewById(R.id.iv_photo);

        initImageFetcher();

        mImageFetcher.loadImage("http://pic1.nipic.com/2008-11-05/200811595452584_2.jpg", photoView);
    }

    private void initImageFetcher() {
        ImageWorker.setDebug(true);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int size = dm.widthPixels > dm.heightPixels ? dm.widthPixels : dm.heightPixels;

        ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams("images");

        /* Allocate a third of the per-app memory limit to the bitmap memory cache. This value
         * should be chosen carefully based on a number of factors. Refer to the corresponding
         * Android Training class for more discussion:
         * http://developer.android.com/training/displaying-bitmaps/
         * In this case, we aren't using memory for much else other than this activity and the
         * ImageDetailActivity so a third lets us keep all our sample image thumbnails in memory
         * at once.
         **/
        cacheParams.memCacheSize = 1024 * 1024 * Utils.getMemoryClass(this) / 3;


        // The ImageWorker takes care of loading images into our ImageView children asynchronously
        mImageFetcher = new ImageFetcher(this, size);
        mImageFetcher.setLoadingImage(R.drawable.empty_photo);
        mImageFetcher.setImageCache(new ImageCache(this, cacheParams));
    }
}
