package document_access;

public interface DocumentService {

    String readDocument(String documentId, String userRole);
}
