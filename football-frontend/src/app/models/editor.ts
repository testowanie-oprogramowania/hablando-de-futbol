export class Editor {
  id?: number;
  name: string;
  surname: string;
  photoUrl: string;

  constructor(name: string, surname: string, photoUrl: string, id?: number) {
    this.name = name;
    this.surname = surname;
    this.photoUrl = photoUrl;
    this.id = id;
  }
}
