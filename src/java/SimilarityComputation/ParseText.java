/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimilarityComputation;

/**
 *
 * @author Hamza
 */
public class ParseText {
    
//    class Parse {

        private String title;
        private String meta;
        private String text;

        ParseText() {
//            title = new String();
//            meta = new String();
//            text = new String();
            title = "";
            meta = "";
            text = "";
        }

        public String getTitle() {
            return this.title;
        }

        public String getMeta() {
            return this.meta;
        }

        public String getText() {
            return this.text;
        }

        public void setTitle(String s) {
            this.title = s;
        }

        public void setMeta(String s) {
            this.meta = s;
        }

        public void setText(String s) {
            this.text = s;
        }
//    }
}
