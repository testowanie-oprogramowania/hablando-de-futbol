export class Comment {
    constructor(
        public nickname: string,
        public content: string,
        public thumbsUp: number,
        public thumbsDown: number,
        public id: number,
        public isLikedByUser = false,
        public isDislikedByUser = false
    ) {}
}
