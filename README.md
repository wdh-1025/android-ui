# android-ui
帮助快速开发android，项目集成6.0权限申请，常用dialog，本地图片选择等...

`项目部分引用第三方开源库，感谢开源`

项目正在不断有空更新， 别急，先star吧...
* 图片选择(单选/多选)
``` android
    @OnClick(R.id.image_choose)
    public void imageChoose() {
        PhotoPickerIntent intent = new PhotoPickerIntent(this);
        intent.setShowCamera(true);
        intent.setTailoring(true);
        intent.setPhotoCount(1);
        intent.setImageSelect(new PhotoPickerActivity.CallbackHead() {
            @Override
            public void selectResults(Bitmap bitmap, List<String> selectedPhotos) {
                //注意:如果选择的是多张图片的话bitmap为null,只返回selectedPhotos
                if (bitmap != null) {
                    imageChoose.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(MainActivity.this, "选择了" + selectedPhotos.size() + "张照片", Toast.LENGTH_SHORT).show();
                }
            }
        });
        startActivity(intent);
    }
```
*  权限申请
``` android
    Permission.getInstance(this)
                .requestPermission(Manifest.permission.CAMERA)
                .results(new ResultCallBack() {
                    @Override
                    public void result(PermissionsResult result) {
                        if (result.isGranted()) {
                            //有权限
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 1);
                            Toast.makeText(MainActivity.this, "有权限啦", Toast.LENGTH_SHORT).show();
                        } else {
                            //被拒绝
                            Toast.makeText(MainActivity.this, "用户拒绝了你的申请", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
```
[其它请自行下载demo查看](http://bmob-cdn-7815.b0.upaiyun.com/2016/11/28/3655363f40426023800e8e12070e56d7.apk)

QQ群：392456615 欢迎加入讨论、分享;
