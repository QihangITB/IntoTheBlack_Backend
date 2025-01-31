namespace API.Models
{
    public class CollectionDTO
    {
        public int Id { get; set; }
        public List<int> FragmentList { get; set; } = new List<int>();
    }
}
