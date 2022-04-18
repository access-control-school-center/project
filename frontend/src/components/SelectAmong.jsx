const SelectAmong = ({ options, value, onChange }) => {
  return (
    <select
      value={value}
      onChange={onChange}
    >
      {options.map((opt) => (
        <option key={opt} value={opt}>{opt}</option>
      ))}
    </select>
  )
}

export default SelectAmong