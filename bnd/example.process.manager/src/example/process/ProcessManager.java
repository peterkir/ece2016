package example.process;

import java.util.List;
import java.util.Optional;

public interface ProcessManager {
    List<ProcessHandle> listUserProcesses();
    Optional<ProcessHandle> startProcess(List<String> command);
    boolean killProcess(long pid);
    List<ProcessHandle> listChildProcesses(long parentPid);
}
