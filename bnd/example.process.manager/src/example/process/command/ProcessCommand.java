package example.process.command;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import example.process.ProcessManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component(
    service = Object.class,
    property = {
        "osgi.command.scope=proc",
        "osgi.command.function=list",
        "osgi.command.function=listDetail",
        "osgi.command.function=start",
        "osgi.command.function=kill",
        "osgi.command.function=children"
    }
)
public class ProcessCommand {

    @Reference
    private ProcessManager manager;

    public void list() {
        manager.listUserProcesses().forEach(ph ->
            System.out.printf("PID: %d, CMD: %s%n", ph.pid(), ph.info().command().orElse("<unknown>")));
    }

    public void listDetail() {
        List<ProcessHandle> userProcesses = manager.listUserProcesses();
        
        // Group processes by parent PID
        Map<Long, List<ProcessHandle>> processByParent = userProcesses.stream()
            .collect(Collectors.groupingBy(ph -> 
                ph.parent().map(ProcessHandle::pid).orElse(-1L)));
        
        // Find root processes (those without parents in our user process list)
        List<ProcessHandle> rootProcesses = userProcesses.stream()
            .filter(ph -> ph.parent().isEmpty() || 
                !userProcesses.contains(ph.parent().get()))
            .sorted((p1, p2) -> Long.compare(p1.pid(), p2.pid()))
            .collect(Collectors.toList());
        
        // Display process tree
        for (ProcessHandle root : rootProcesses) {
            displayProcessTree(root, processByParent, 0);
        }
    }
    
    private void displayProcessTree(ProcessHandle process, Map<Long, List<ProcessHandle>> processByParent, int indentLevel) {
        // Print current process with appropriate indentation
        String indent = "    ".repeat(indentLevel);
        System.out.printf("%sPID: %d, CMD: %s%n", 
            indent, 
            process.pid(), 
            process.info().command().orElse("<unknown>"));
        
        // Get and display children
        List<ProcessHandle> children = processByParent.getOrDefault(process.pid(), List.of());
        children.stream()
            .sorted((p1, p2) -> Long.compare(p1.pid(), p2.pid()))
            .forEach(child -> displayProcessTree(child, processByParent, indentLevel + 1));
    }

    public void start(String... args) {
        List<String> command = List.of(args);
        manager.startProcess(command)
            .ifPresentOrElse(
                h -> System.out.println("Started process with PID: " + h.pid()),
                () -> System.out.println("Failed to start process.")
            );
    }

    public void kill(long pid) {
        boolean success = manager.killProcess(pid);
        System.out.println(success ? "Killed process " + pid : "Failed to kill process " + pid);
    }

    public void children(long parentPid) {
        // First, display the parent process
        ProcessHandle.of(parentPid).ifPresentOrElse(
            parent -> {
                System.out.printf("PID: %d, CMD: %s%n", 
                    parent.pid(), 
                    parent.info().command().orElse("<unknown>"));
                
                // Then display child processes with 4-space indentation
                List<ProcessHandle> childProcesses = manager.listChildProcesses(parentPid);
                if (childProcesses.isEmpty()) {
                    System.out.println("    (no child processes)");
                } else {
                    childProcesses.forEach(ph ->
                        System.out.printf("    PID: %d, CMD: %s%n", 
                            ph.pid(), 
                            ph.info().command().orElse("<unknown>")));
                }
            },
            () -> System.out.println("Process with PID " + parentPid + " not found or not alive")
        );
    }
}
