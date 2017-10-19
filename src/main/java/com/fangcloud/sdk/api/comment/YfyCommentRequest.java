package com.fangcloud.sdk.api.comment;

import com.fangcloud.sdk.YfyBaseClient;
import com.fangcloud.sdk.YfySdkConstant;
import com.fangcloud.sdk.api.SuccessResult;
import com.fangcloud.sdk.exception.YfyException;

public class YfyCommentRequest {
    private final static String COLLAB_PATH = YfySdkConstant.API_VERSION + "comment/";
    private final static String CREATE_COMMENT_PATH = COLLAB_PATH + "create";
    private final static String DELETE_COMMENT_PATH = COLLAB_PATH + "%s/delete";

    private final YfyBaseClient<?>.YfyInternalClient client;

    public YfyCommentRequest(YfyBaseClient.YfyInternalClient client) {
        this.client = client;
    }

    /**
     * Create comment on a file
     *
     * @param fileId File id in fangcloud
     * @param content Content of the comment
     * @return Detailed comment information
     * @throws YfyException
     */
    public YfyComment createComment(long fileId, String content) throws YfyException {
        return createComment(new CreateCommentArg(fileId, content));
    }

    private YfyComment createComment(CreateCommentArg createCommentArg) throws YfyException {
        return client.doPost(CREATE_COMMENT_PATH,
                null,
                createCommentArg,
                YfyComment.class);
    }

    /**
     * Delete comment by comment id
     *
     * @param commentId Comment id
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult deleteComment(long commentId) throws YfyException {
        String[] params = { String.valueOf(commentId) };
        return deleteComment(params);
    }

    private SuccessResult deleteComment(String[] params) throws YfyException {
        return client.doPost(DELETE_COMMENT_PATH,
                params,
                null,
                SuccessResult.class);
    }



}
