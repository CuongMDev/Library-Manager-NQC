package com.example.librarymanagernqc.ManagementInterface.BorrowedList;

import com.example.librarymanagernqc.Objects.BookLoan.BookLoan;
import com.example.librarymanagernqc.ManagementInterface.BorrowedList.RecordBookReturn.RecordBookReturnController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Objects;

public class BorrowedListController {
    @FXML
    private TreeTableColumn<BookLoan, Void> optionColumn;
    @FXML
    private StackPane mainStackPane;

    @FXML
    private void initialize() {
        optionColumn.setCellFactory(new Callback<>() {
            @Override
            public TreeTableCell<BookLoan, Void> call(TreeTableColumn<BookLoan, Void> param) {
                return new TreeTableCell<>() {
                    private final JFXButton recordButton = new JFXButton();
                    {
                        //create add Image
                        ImageView recordImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/record.png")))); // Đường dẫn tới ảnh
                        recordImage.setFitWidth(20); // Đặt kích thước cho ảnh
                        recordImage.setFitHeight(20);

                        //set recordButton

                        recordButton.setRipplerFill(Color.WHITE);
                        recordButton.setCursor(Cursor.HAND);
                        recordButton.setGraphic(recordImage);
                        recordButton.setOnAction(event -> {
                            //lấy ô hiện tại đang chọn
                            BookLoan currentBook = getTreeTableView().getSelectionModel().getSelectedItem().getValue();
                            //load book information
                            Pane savePane = (Pane) mainStackPane.getChildren().removeLast();
                            FXMLLoader recordBookReturnLoader = new FXMLLoader(getClass().getResource("RecordBookReturn/record-book-return.fxml"));
                            try {
                                mainStackPane.getChildren().add(recordBookReturnLoader.load());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            RecordBookReturnController recordBookReturnController = recordBookReturnLoader.getController();
                            recordBookReturnController.addBook(currentBook);

                            //record button event
                            recordBookReturnController.recordButton.setOnMouseClicked(recordMouseEvent -> {
                                if (recordMouseEvent.getButton() == MouseButton.PRIMARY) {


                                    mainStackPane.getChildren().removeLast();
                                    mainStackPane.getChildren().add(savePane);
                                }
                            });

                            //cancel button event
                            recordBookReturnController.cancelButton.setOnMouseClicked(cancelMouseEvent -> {
                                if (cancelMouseEvent.getButton() == MouseButton.PRIMARY) {
                                    mainStackPane.getChildren().removeLast();
                                    mainStackPane.getChildren().add(savePane);
                                }
                            });

                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(recordButton);
                        }

                        // Căn giữa ảnh trong ô
                        setStyle("-fx-alignment: CENTER;");
                    }
                };
            }
        });
    }

}
