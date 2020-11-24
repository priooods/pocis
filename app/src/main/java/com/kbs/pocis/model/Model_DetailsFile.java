package com.kbs.pocis.model;

public class Model_DetailsFile {
    /*
                    "id": 10127,
                            "t_booking_id": "36538",
                            "m_document_id": "23",
                            "path": "files/",
                            "filename": "AGTFLS-B0001-2020-00010-20201021103045.pdf",
                            "documents": {
            "id": 23,
                    "code": "AGTFLS",
                    "description": "BERKAS PENAMBATAN KAPAL ( PKK, SHIP PARTICULARS, B/L OR SI ,CARGO MANIFEST, STOWAGE PLAN ) ",
                    "ext": "pdf",
                    "size": "2000"
        }
     */
    int id;
    String t_booking_id, m_document_id, path, filename;
    DetailsFile_Doc documents;
    //String comodityName, comodityTyoe;
    //String packageNo, tonage;

    public class DetailsFile_Doc {
        int id;
        String code, description, ext, size;
    }
/*
    public Model_DetailsFile(String comodityName, String comodityTyoe, String packageNo, String tonage) {
        this.comodityName = comodityName;
        this.comodityTyoe = comodityTyoe;
        this.packageNo = packageNo;
        this.tonage = tonage;
    }*/
}