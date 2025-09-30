package proxy;


import document_access.DocumentService;
import document_access.RealDocumentService;

import java.util.Arrays;
import java.util.List;


public class AccessControlProxy implements DocumentService {
    
    private DocumentService realService;
    private final String documentId;
    private final List<String> authorizedRoles = Arrays.asList("ADMIN", "MANAGER");

    public AccessControlProxy(String documentId) {
        this.documentId = documentId;
    }

    @Override
    public String readDocument(String documentId, String userRole) {
        // 1. Pre-fetch check (Access Control) - This is the primary function of this proxy.
        if (!authorizedRoles.contains(userRole.toUpperCase())) {
            System.out.println("\n[PROXY] Access DENIED! Role '" + userRole + "' is not authorized to read document " + documentId);
            return "[ACCESS DENIED]";
        }

        // 2. Lazy Initialization (if Real Subject hasn't been created yet)
        if (realService == null) {
            System.out.println("\n[PROXY] Access GRANTED. Initializing Real Document Service...");
            realService = new RealDocumentService(this.documentId);
        } else {
            System.out.println("\n[PROXY] Access GRANTED. Using existing Real Document Service instance.");
        }

        // 3. Delegate the request to the Real Subject
        return realService.readDocument(documentId, userRole);
    }
}
