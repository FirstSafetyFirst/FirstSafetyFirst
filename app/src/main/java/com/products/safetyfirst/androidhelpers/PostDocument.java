package com.products.safetyfirst.androidhelpers;

import com.google.firebase.firestore.DocumentSnapshot;

/**
 * Created by ishita sharma on 12/14/2017.
 */

public class PostDocument {

    DocumentSnapshot postDocument, userDocument;

    public PostDocument(DocumentSnapshot postDocument, DocumentSnapshot userDocument) {
        this.postDocument = postDocument;
        this.userDocument =userDocument;
    }

    public DocumentSnapshot getPostDocument() {
        return postDocument;
    }

    public DocumentSnapshot getUserDocument() {
        return userDocument;
    }
}
