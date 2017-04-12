/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kunall17.textbuilder;


class RowData {
    int size;
    String[] data;

    public RowData(int size) {
        this.size = size;
        data = new String[size];
    }

    public String[] getData() {
        return data;
    }

    public String getData(int index) {
        return data[index];
    }

    public void setData(String data, int index) {
        this.data[index] = data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = "";
        }
    }
}
