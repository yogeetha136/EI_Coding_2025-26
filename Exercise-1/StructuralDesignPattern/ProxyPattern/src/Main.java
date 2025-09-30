import document_access.DocumentService;
import proxy.AccessControlProxy;


public class Main {

    public static void main(String[] args) {
        System.out.println("--- Scenario 1: User with high security clearance (ADMIN) ---");
        // The client only interacts with the DocumentService interface, passing the Proxy.
        DocumentService adminClient = new AccessControlProxy("Doc-A47-CONFIDENTIAL");
        
        // First access: Real Subject is initialized, delay occurs.
        System.out.println("ADMIN attempting first read...");
        long startTime1 = System.currentTimeMillis();
        System.out.println(adminClient.readDocument("Doc-A47-CONFIDENTIAL", "ADMIN"));
        long elapsed1 = System.currentTimeMillis() - startTime1;
        System.out.println("Time elapsed: " + elapsed1 + "ms");

        // Second access: Real Subject is already initialized, re-initialization.
        System.out.println("\nADMIN attempting second read...");
        long startTime2 = System.currentTimeMillis();
        System.out.println(adminClient.readDocument("Doc-A47-CONFIDENTIAL", "ADMIN"));
        long elapsed2 = System.currentTimeMillis() - startTime2;
        System.out.println("Time elapsed: " + elapsed2 + "ms (Faster due to service being initialized)");


        System.out.println("\n\n--- Scenario 2: User with low security clearance (VIEWER) ---");
        DocumentService viewerClient = new AccessControlProxy("Doc-A47-CONFIDENTIAL");
        
        // Viewer attempts read: Access is denied by the Proxy before initializing the costly Real Subject.
        System.out.println("VIEWER attempting read...");
        long startTime3 = System.currentTimeMillis();
        System.out.println(viewerClient.readDocument("Doc-A47-CONFIDENTIAL", "VIEWER"));
        long elapsed3 = System.currentTimeMillis() - startTime3;
        System.out.println("Time elapsed: " + elapsed3 + "ms (Instant access denial)");

    }
}
