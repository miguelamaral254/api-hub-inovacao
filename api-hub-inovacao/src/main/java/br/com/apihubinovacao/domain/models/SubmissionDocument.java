package br.com.apihubinovacao.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "SUBMISSION_DOCUMENT")
public class SubmissionDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDOCUMENT")
    private Long idDocument;

    @Lob
    @Column(name = "document_file", nullable = false, columnDefinition = "MEDIUMBLOB")
    private byte[] documentFile;

    @ManyToOne
    @JoinColumn(name = "SUBMISSION_idSUBMISSION")
    private Submission submission;

    public Long getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Long idDocument) {
        this.idDocument = idDocument;
    }

    public byte[] getDocumentFile() {
        return documentFile;
    }

    public void setDocumentFile(byte[] documentFile) {
        this.documentFile = documentFile;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }
}

