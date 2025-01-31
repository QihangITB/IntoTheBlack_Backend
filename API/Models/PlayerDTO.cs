namespace API.Models
{
    public class PlayerDTO
    {
        public int Id { get; set; }
        public string? Username { get; set; }
        public string? Email { get; set; }
        public string? Password { get; set; }
        public string? RecordTime { get; set; }
        public int CollectionId { get; set; }
    }
}
