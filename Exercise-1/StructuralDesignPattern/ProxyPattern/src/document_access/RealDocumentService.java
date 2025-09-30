package document_access;


import java.util.concurrent.TimeUnit;

public class RealDocumentService implements DocumentService {

    private String documentName;

    public RealDocumentService(String documentName) {
        this.documentName = documentName;
        // Simulate loading the large document into memory
        loadDocument();
    }

    private void loadDocument() {
        System.out.println("  [RealService] Initializing: Loading large document '" + documentName + "'...");
        try {
            TimeUnit.SECONDS.sleep(2); // Simulate a slow operation
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("  [RealService] Document loaded successfully.");
    }

    
     //This is the sensitive operation that the Proxy will guard.
    @Override
    public String readDocument(String documentId, String userRole) {
        System.out.println("  [RealService] Reading and transferring data for " + documentId + " to user with role: " + userRole);
        return "--- CONFIDENTIAL CONTENT OF DOCUMENT " + documentId + " ---";
    }
}
