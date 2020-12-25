package com.zy.devicelibrary.data;

import com.zy.devicelibrary.utils.FileUtils;

import static com.zy.devicelibrary.utils.MediaFilesUtils.getAudioExternal;
import static com.zy.devicelibrary.utils.MediaFilesUtils.getAudioInternal;
import static com.zy.devicelibrary.utils.MediaFilesUtils.getDownloadFilesCount;
import static com.zy.devicelibrary.utils.MediaFilesUtils.getImagesExternal;
import static com.zy.devicelibrary.utils.MediaFilesUtils.getImagesInternal;
import static com.zy.devicelibrary.utils.MediaFilesUtils.getVideoExternal;
import static com.zy.devicelibrary.utils.MediaFilesUtils.getVideoInternal;
import static com.zy.devicelibrary.utils.OtherUtils.getContactGroupCount;

public class MediaFilesData {

    public int audio_internal;
    public int audio_external;
    public int images_internal;
    public int images_external;
    public int video_internal;
    public int video_external;
    public int download_files;
    public int contact_group;

    {
        audio_internal = getAudioInternal();
        audio_external = getAudioExternal();
        images_internal = getImagesInternal();
        images_external = getImagesExternal();
        video_internal = getVideoInternal();
        video_external = getVideoExternal();
        download_files = getDownloadFilesCount();
        contact_group = getContactGroupCount();

    }

}
