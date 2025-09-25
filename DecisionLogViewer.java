import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;

/**
 * GUI to browse per-run files in reports/ and view their contents.
 */
public class DecisionLogViewer extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JList<File> fileList;
    private final DefaultListModel<File> listModel;
    private final JTextArea textArea;
    private final JButton refreshButton;
    private final JButton closeButton;

    public DecisionLogViewer() {
        super("Decision Log Viewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroll = new JScrollPane(fileList);
        listScroll.setPreferredSize(new Dimension(260, 600));

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane textScroll = new JScrollPane(textArea);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScroll, textScroll);
        split.setResizeWeight(0.25);
        add(split, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        refreshButton = new JButton("Refresh");
        closeButton = new JButton("Close");
        buttons.add(refreshButton);
        buttons.add(closeButton);
        add(buttons, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> loadFileList());
        closeButton.addActionListener(e -> dispose());

        fileList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                File f = fileList.getSelectedValue();
                if (f != null) {
                    loadFile(f);
                }
            }
        });

        loadFileList();
    }

    private void loadFileList() {
        listModel.clear();
        File reports = new File("reports");
        if (!reports.exists() || !reports.isDirectory()) {
            textArea.setText("No reports directory found. Run DecisionAid to create per-run report files in 'reports/'.");
            return;
        }
        File[] files = reports.listFiles((dir, name) -> name.startsWith("decision_") && name.endsWith(".txt"));
        if (files == null || files.length == 0) {
            textArea.setText("No decision_*.txt files found in 'reports/'.");
            return;
        }
        Arrays.sort(files, (a,b) -> b.getName().compareTo(a.getName())); // newest first
        for (File f : files) {
            listModel.addElement(f);
        }
        fileList.setSelectedIndex(0);
    }

    private void loadFile(File f) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            textArea.setText(sb.toString());
            textArea.setCaretPosition(0);
        } catch (IOException ioe) {
            textArea.setText("Error reading file: " + ioe.getMessage());
        }
    }

    public static void showViewer(Component parent) {
        SwingUtilities.invokeLater(() -> {
            DecisionLogViewer v = new DecisionLogViewer();
            v.setVisible(true);
        });
    }
}
