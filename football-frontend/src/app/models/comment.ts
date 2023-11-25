export class Comment {
    id?: number;
    nickname: string;
    content: string;
    thumbsUp: number;
    thumbsDown: number;

    constructor(nickname: string, content: string, thumbsUp: number, thumbsDown: number, id?: number) {
        this.nickname = nickname;
        this.content = content;
        this.thumbsUp = thumbsUp;
        this.thumbsDown = thumbsDown;
        this.id = id;
    }
}
