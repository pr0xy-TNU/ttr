public enum  Language {
    ENG("English"),
    PT("Portugual"),
    IT("Italian"),
    SP("Spainash");

    private Language(String name){
        this.language = name;
    }
    public String language;
    public String getLanguage(){
        return language;
    }

    /*public Language getByString(String language){
        for (Language item : values()
             ) {
            if (item.getLanguage().equals(language)){

            }
        }
    }*/

}
